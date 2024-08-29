document.addEventListener('DOMContentLoaded', function(){
    const tasksrow = document.querySelector('#tasksrow'); //div to add
    const form = document.querySelector('#taskForm');
    const togglebutton = document.querySelector('.togglebutton');
    let tasks = [];

    //dialog booxs
    const editTaskForm = document.querySelector('#editTaskForm');
    const addsubtaskform = document.querySelector('#addsubtaskform');
    const editSubtaskForm = document.querySelector('#editSubtaskForm');

    //priority
    const prioritysortbutton = document.querySelector('.prioritysortbutton');
    const datesortbutton = document.querySelector('.datesortbutton');

    //export and import
    const importbutton = document.querySelector('.importbutton');
    const exportbutton = document.querySelector('.exportbutton');

    //searching
    const searchbutton = document.querySelector('.searchbutton');
    const searchinput = document.querySelector('.searchinput');

    //logout
    const logoutbutton = document.querySelector('.logoutbutton');

    //indexs
    let currenttaskindex = null;
    let currentsubtaskindex = null;

    function addingtasks(filteredTasks = tasks) {
        tasksrow.innerHTML = '';
        filteredTasks.forEach((task, taskIndex) => {
            const taskdiv = document.createElement('div');
            taskdiv.setAttribute('class', 'col-md-12 mb-3 divdrag');
            taskdiv.setAttribute('draggable', 'true');
            taskdiv.dataset.type = 'task';
            taskdiv.dataset.index = taskIndex;

            taskdiv.innerHTML = `
                <div class='card' draggable='true'>
                    <div class='card-body'>
                        <h5 class='card-title'>${task.TaskName}</h5>
                        <p class="card-text">Priority: ${task.Priority}</p>
                        <p class="card-text">Date: ${task.Date}</p>
                        <button class="btn btn-primary me-2 edittaskbutton" data-id="${task.TaskId}" data-index="${taskIndex}" data-bs-toggle="modal" data-bs-target="#edittaskmodal">Edit</button>
                        <button class="btn btn-secondary me-2 addsubtaskbutton" data-id="${task.TaskId}" data-index="${taskIndex}" data-bs-toggle="modal" data-bs-target="#addsubtaskmodal">Add Subtask</button>
                        <button class="btn btn-danger deletetaskbutton" data-id="${task.TaskId}" data-index="${taskIndex}">Delete</button>
                        <div class="subtasksdiv mt-3">
                            <h6>SubTasks</h6>
                            <ul class="list-group">

                            </ul>
                        </div>
                    </div>
                </div>
            `;

            tasksrow.appendChild(taskdiv);
        });
    }


    // taking tasks
    form.addEventListener('submit', (e) => {
        e.preventDefault(); // Prevent the default form submission behavior

        // Collect input values
        const taskInput = document.querySelector('#taskInput').value.trim();
        const priorityInput = document.querySelector('#priorityInput').value.trim();
        const dateInput = document.querySelector('#dateInput').value;

        // Validate inputs
        if (taskInput === '' || priorityInput === '' || dateInput === '') {
            alert("Please fill out all the fields.");
            return; // Exit the function if validation fails
        }

        // Create the task object to be sent
        const taskData = {
            task: taskInput,
            priority: priorityInput,
            dateTime: dateInput
        };

        // Send the fetch request
        fetch('/Todo_App/addtask', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(taskData),
        })
        .then(response => {
            if (!response.ok) {
                // Check if the response status is OK (status code 200-299)
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json(); // Parse JSON response if the request was successful
        })
        .then(data => {
            // Handle the successful response from the server
            console.log("Task added successfully:", data);
            console.log("Received task ID:", data.taskId);

            tasks.push({
                id : data.taskId,
                task: taskInput,
                priority: priorityInput,
                dateTime: dateInput,
                subtasks: []
            });
            console.log("Tasks array after addition:", tasks);
            // Calling the functions
//            saveTasks();
            loadtasks();
        })
        .catch(error => {
            // Handling the errors
            console.error('There was a problem with the fetch operation:', error);
        });
    });

    tasksrow.addEventListener('click',(event) =>{
        const target = event.target;
        if(target.matches('.edittaskbutton')){
            const thistaskid = target.getAttribute('data-id');
            edittaskfunction(event,thistaskid);
        }else if(target.matches('.addsubtaskbutton')){
            addsubtaskfunction(event);
        }else if(target.matches('.deletetaskbutton')){
            const thistaskid = target.getAttribute('data-id');
            deletetaskfunction(event,thistaskid);
        }else if(target.matches('.editsubtaskbutton')){
            const thistaskid = target.getAttribute('data-id');
            editsubtaskfunction(event,thistaskid);
        }else if(target.matches('.deletesubtaskbutton')){
            const thistaskid = target.getAttribute('data-id');
            deletesubtaskfunction(event,thistaskid);
        }
    })



    //edit task
    function edittaskfunction(event,thistaskid){
        currenttaskindex = parseInt(event.target.getAttribute('data-index'), 10);
        const task = tasks[currenttaskindex];
        const thetaskId = parseInt(thistaskid, 10);

        // Populate it
        document.querySelector('#edittaskinput').value = task.task;
        document.querySelector('#editpriorityinput').value = task.priority;
        document.querySelector('#editdateinput').value = task.dateTime;
        document.querySelector('#editTaskForm').dataset.taskId = thetaskId;
    }

    editTaskForm.addEventListener('submit', (e) => {
        e.preventDefault();

        const edittaskinput = document.querySelector("#edittaskinput").value.trim();
        const editpriorityinput = document.querySelector("#editpriorityinput").value.trim();
        const editdateinput = document.querySelector("#editdateinput").value;
        const taskId = parseInt(document.querySelector('#editTaskForm').dataset.taskId, 10);
        console.log("Task-Id",taskId);

        if (isNaN(taskId)) {
             console.error('Invalid taskId:', taskId);
             return;
        }


        // Validate inputs
        if (edittaskinput === '' || editpriorityinput === '' || editdateinput === '') {
            alert("Fill out all the fields");
            return;
        }

        // Prepare task data
        const updatedTask = {
             id : taskId,
            task: edittaskinput,
            priority: editpriorityinput,
            dateTime: editdateinput,
            subtasks: tasks[currenttaskindex].subtasks // Preserve existing subtasks
        };

        // Send update request
        fetch('/Todo_App/updatetask', { // Change the URL to the appropriate endpoint for updating
            method: 'PUT', // Use PUT for updates
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                taskId: taskId,
                task: updatedTask
            }),
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.json(); // Parse JSON response
        })
        .then(data => {
            console.log("Task updated successfully:", data);

            // Update the local tasks array and UI
            tasks[currenttaskindex] = updatedTask;

//            saveTasks();
            loadtasks();
            currenttaskindex = null;

            // Hide the modal and clear the form
            const editTaskModal = bootstrap.Modal.getInstance(document.querySelector('#edittaskmodal'));
            if (editTaskModal) editTaskModal.hide();
            document.querySelector('#edittaskinput').value = '';
            document.querySelector('#editpriorityinput').value = '';
            document.querySelector('#editdateinput').value = '';
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
    });




    //addsubtask form
    function addsubtaskfunction(event) {
        currenttaskindex = parseInt(event.target.getAttribute('data-index'), 10);
        console.log('you have clicked addsubtask');
    }

    addsubtaskform.addEventListener('submit', (e) => {
        e.preventDefault();
        console.log("you have submitted the addsubtaskform");
        const subtaskInput = document.querySelector('#subtaskinput').value;
        const subtaskPriorityInput = document.querySelector('#subtaskpriorityinput').value;
        const subtaskDateTimeInput = document.querySelector('#subtaskdatetimeinput').value;

        if (subtaskInput.trim() === '' || subtaskPriorityInput.trim() === '' || subtaskDateTimeInput.trim() === '') {
            alert("Fill out all the fields");
            return;
        }

        if (currenttaskindex === null || currenttaskindex < 0 || currenttaskindex >= tasks.length) {
            alert("Invalid task index.");
            return;
        }

        const subtaskData = {
            subtask: subtaskInput,
            priority: subtaskPriorityInput,
            dateTime: subtaskDateTimeInput,
            taskId: tasks[currenttaskindex].id
        };

        fetch('/Todo_App/addsubtask', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(subtaskData),
        })
        .then(response => {
            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
            return response.text();
        })
        .then(text => {
            try {
                const data = JSON.parse(text);
                console.log(data.subtaskId)
                tasks[currenttaskindex].subtasks.push({
                    id: data.subtaskId,
                    subtask: subtaskInput,
                    priority: subtaskPriorityInput,
                    dateTime: subtaskDateTimeInput
                });
//                saveTasks();
                addingtasks();
                currenttaskindex = null;
                const addSubtaskModal = bootstrap.Modal.getInstance(document.querySelector('#addsubtaskmodal'));
                if (addSubtaskModal) addSubtaskModal.hide();
                document.querySelector('#subtaskinput').value = '';
                document.querySelector('#subtaskpriorityinput').value = '';
                document.querySelector('#subtaskdatetimeinput').value = '';
            } catch (error) {
                console.error('Failed to parse JSON response:', error);
            }
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
    });



    //deleting all tasks
    document.querySelector('#deleteAllTasks').addEventListener('click', (e) => {
        if (confirm('Are you sure you want to delete all tasks?')) {
            fetch('/Todo_App/deletealltasks', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({})
            })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.json();
            })
            .then(data => {
                console.log("All tasks deleted successfully:", data);
                  tasks = [];
//                  saveTasks();
                  addingtasks();
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
        }
    });

    //deleting task button function
    function deletetaskfunction(event, thistaskid) {
        console.log("You have come here");

        // Parse taskId from the function parameter
        const thetaskId = parseInt(thistaskid, 10);

        // Validate taskId
        if (isNaN(thetaskId)) {
            console.error('Invalid taskId:', thetaskId);
            return;
        }

        console.log('Data-id attribute:', thetaskId);

        if (confirm('Are you sure you want to delete this task?')) {
            fetch('/Todo_App/deletetask', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    taskId: thetaskId
                }),
            })
            .then(response => {
                if (response.ok) {
                    return response.text(); // Changed to text if your API returns plain text
                } else {
                    throw new Error('Network response was not ok.');
                }
            })
            .then(text => {
                console.log('Server response:', text);
                // Remove the task from the local array
                tasks = tasks.filter(task => task.id !== thetaskId);
//                saveTasks();
                loadtasks();
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
            });
        }
    }







    //editing subtasks
    function editsubtaskfunction(event,thistaskid){
        currenttaskindex = parseInt(event.target.getAttribute('data-task-index'), 10);
        currentsubtaskindex = parseInt(event.target.getAttribute('data-subtask-index'), 10);
        const thetaskId = parseInt(thistaskid, 10);

        const subtask = tasks[currenttaskindex].subtasks[currentsubtaskindex];

        //populate it
        document.querySelector('#editSubtaskInput').value = subtask.subtask;
        document.querySelector('#editSubtaskPriorityInput').value =  subtask.priority;
        document.querySelector('#editSubtaskDateTimeInput').value = new Date(subtask.dateTime).toISOString().slice(0, 16);
        document.querySelector('#editSubtaskForm').dataset.subtaskId = thetaskId;
    }
    editSubtaskForm.addEventListener('submit',(e) =>{
        e.preventDefault();
        const editSubtaskInput = document.querySelector('#editSubtaskInput').value.trim();
        const editSubtaskPriorityInput = document.querySelector('#editSubtaskPriorityInput').value.trim();
        const editSubtaskDateTimeInput = document.querySelector('#editSubtaskDateTimeInput').value.trim();
        const subtaskId = parseInt(document.querySelector('#editSubtaskForm').dataset.subtaskId, 10);

        if(editSubtaskInput === '' || editSubtaskPriorityInput ==='' || editSubtaskDateTimeInput===''){
            alert("Fill out all the fields");
            return;
        }

        const updatedSubtask = {
               id : subtaskId,
               subtask : editSubtaskInput,
               priority: editSubtaskPriorityInput,
               dateTime: editSubtaskDateTimeInput
        };


        fetch('/Todo_App/updatesubtask', {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify({
                        subtaskId : subtaskId,
                        subtask: updatedSubtask
                    }),
                })
                .then(response => {
                    if (!response.ok) {
                        throw new Error(`HTTP error! Status: ${response.status}`);
                    }
                    return response.json();
                })
                .then(data => {
                       console.log("Subtask updated successfully:", data);
                       tasks[currenttaskindex].subtasks[currentsubtaskindex] = {
                                    id : subtaskId,
                                   subtask : editSubtaskInput,
                                   priority: editSubtaskPriorityInput,
                                   dateTime: editSubtaskDateTimeInput
                       };
//                        saveTasks();
                        addingtasks();
                        currenttaskindex = null;
                        currenttaskindex = null;
                        const editTaskModal = bootstrap.Modal.getInstance(document.querySelector('#editsubtasksmodal'));
                        if (editTaskModal) editTaskModal.hide();
                        document.querySelector('#editSubtaskInput').value = '';
                        document.querySelector('#editSubtaskPriorityInput').value =  '';
                        document.querySelector('#editSubtaskDateTimeInput').value = '';
                })
                .catch(error => {
                    console.error('There was a problem with the fetch operation:', error);
                });


    });

    //deleting subtasks
    function deletesubtaskfunction(event, thistaskid) {
        const thetaskId = parseInt(thistaskid, 10);
        const taskIndex = parseInt(event.target.getAttribute('data-task-index'), 10);
        const subtaskIndex = parseInt(event.target.getAttribute('data-subtask-index'), 10);

        if (isNaN(thetaskId)) {
            console.error('Invalid taskId:', thetaskId);
            return;
        }

        if (isNaN(taskIndex) || isNaN(subtaskIndex)) {
            console.error('Invalid indices:', { taskIndex, subtaskIndex });
            return;
        }

        if (taskIndex < 0 || taskIndex >= tasks.length) {
            console.error('Task index out of bounds:', taskIndex);
            return;
        }

        if (subtaskIndex < 0 || subtaskIndex >= tasks[taskIndex].subtasks.length) {
            console.error('Subtask index out of bounds:', subtaskIndex);
            return;
        }

        const subtaskId = tasks[taskIndex].subtasks[subtaskIndex].id; // Assuming each subtask has an ID

        fetch('/Todo_App/deletesubtask', {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({
                taskId: thetaskId,
                subtaskId: subtaskId
            }),
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                throw new Error('Network response was not ok.');
            }
        })
        .then(text => {
            console.log('Server response:', text);
            tasks[taskIndex].subtasks.splice(subtaskIndex, 1);
//            saveTasks();
            addingtasks();
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
    }


    //sorting ***
    function priorityToNumber(priority) {
        switch (priority.toLowerCase()) {
            case 'high': return 3;
            case 'medium': return 2;
            case 'low': return 1;
            default: return 0;
        }
    }
    function sortTasksByPriority(tasks) {
        return tasks.map(task => ({
            ...task, //used to copy all the elements of object
            subtasks: task.subtasks.sort((a, b) => priorityToNumber(b.priority) - priorityToNumber(a.priority))
        })).sort((a, b) => priorityToNumber(b.priority) - priorityToNumber(a.priority));
    }
    prioritysortbutton.addEventListener('click',(e) =>{
        e.preventDefault();
        const sortedTasks = sortTasksByPriority(tasks);
//        saveTasks();
        addingtasks(sortedTasks);
    })

    datesortbutton.addEventListener('click',(e)=>{
        e.preventDefault();
        console.log('sorting by date');
        try{
            const sortedtasks = [...tasks].sort((a,b) =>{
                const dateA = new Date(a.dateTime);
                const dateB = new Date(b.dateTime);

                if (isNaN(dateA.getTime()) || isNaN(dateB.getTime())) {
                    throw new Error('Invalid date format encountered.');
                }
                return dateA - dateB;
            });
            addingtasks(sortedtasks);
        } catch(error){
            alert('Error sorting tasks by date:', error);
        }
    })


    //notifications(i am using notifiactions keyword) ***
    function notifications() {
        Notification.requestPermission().then(permission => {
            if (permission === "granted") {
                const notificationTimes = [15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1];
                const notificationsSent = new Map();
                setInterval(() => {
                    const timeNow = new Date();
                    tasks.forEach((task, taskIndex) => {
                        const taskDate = new Date(task.dateTime);
                        const timeDiff = taskDate - timeNow;
                        notificationTimes.forEach(minutes => {
                            const timeThreshold = minutes * 60 * 1000;
                            if (timeDiff > timeThreshold - 60 * 1000 && timeDiff <= timeThreshold && !notificationsSent.get(`${taskIndex}-${minutes}`)) {
                                const notification = new Notification("Task Deadline Approaching", {
                                    body: `Task "${task.task}" is due in ${minutes} minutes.`,
                                    icon: './Assests/Images/personlogo.png',
                                });
                                notification.addEventListener("error", () => {
                                    alert("Please grant permission");
                                });
                                notificationsSent.set(`${taskIndex}-${minutes}`, true);
                            }
                        });
                    });
                }, 60 * 1000);
            } else {
                alert("I think you have disabled permissions for notifications");
            }
        });
    }


    //import and export
    importbutton.addEventListener('click', (e) =>{
        e.preventDefault();
        const fileInput = document.createElement('input');
        fileInput.type = 'file';
        fileInput.accept ='application/JSON';
        fileInput.addEventListener('change',(e) =>{
            const file = event.target.files[0];
            if(file){
                const reader = new FileReader();
                reader.onload = function(e) {
                    try{
                        tasks = JSON.parse(e.target.result);
                        tasks = sortTasksByPriority(tasks);
//                        saveTasks();
                        addingtasks();
                    }catch(error){
                        console.error('error importing tasks:',error);
                    }
                };
                reader.readAsText(file);
            }
        });
        fileInput.click();
    });

    exportbutton.addEventListener('click', function() {
        const blob = new Blob([JSON.stringify(tasks, null, 2)], { type: 'application/json' });
        const url = URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'tasks.json';
        a.click();
        URL.revokeObjectURL(url);
    });

    //searching
    searchbutton.addEventListener('click', (e) =>{
        e.preventDefault();
        const searchvalue = searchinput.value.toLowerCase();
        const includesSearchTerm = (str) => str.toLowerCase().includes(searchvalue);

        const filteredTasks = tasks.filter(task => {
            return includesSearchTerm(task.task) ||
                   includesSearchTerm(task.priority) ||
                   (task.subtasks && task.subtasks.some(subtask =>
                       includesSearchTerm(subtask.subtask) || includesSearchTerm(subtask.priority))
                   );
        });

        addingtasks(filteredTasks);
    });



      function loadtasks() {
          fetch('/Todo_App/taskhandling')
              .then(response => {
                          if (!response.ok) {
                              throw new Error('Network response was not ok ' + response.statusText);
                          }
                          console.log(response.status)
                          return response.json();
                      })
              .then(data => {
                  console.log('Tasks retrieved:', data);
                  tasks = data;
                  displayTasks(tasks);
              })
              .catch(error => {

                  window.location.href="/Todo_App"
                  console.error('Error loading tasks:', error);
              });
      }

      function displayTasks(tasks) {
          addingtasks(tasks);
      }

    //toggle functionality
    let currenttheme = 'white';
    togglebutton.addEventListener('click',()=>{
        if(currenttheme==='white'){
            currenttheme = 'black';
            document.body.style.backgroundColor = 'black';
        }else{
            currenttheme = 'white';
            document.body.style.backgroundColor = 'white';
        }
    });

    //logoutbutton
    logoutbutton.addEventListener('click', (e) => {
        e.preventDefault();
        fetch('/Todo_App/logout', {
            method: 'POST'
        })
        .then(response => {
            if (response.ok) {
                return response.text();
            } else {
                throw new Error('Network response was not ok.');
            }
        })
        .then(text => {
            console.log('Server response:', text);
            window.location.href = '/Todo_App/';
        })
        .catch(error => {
            console.error('There was a problem with the fetch operation:', error);
        });
    });




    //rendering to check
    loadtasks();
    // sortTasksByPriority();
    notifications();
});
