// Get a reference to the table element
const myTable = document.getElementById("lokalTable");

// Add an event listener to the table that listens for button clicks
myTable.addEventListener("click", handleButtonClick);

// Event listener function for button click events
// Event listener function for button click events
function handleButtonClick(event) {
    // Check if the clicked element is an "Update" button
    console.log("Button clicked!"); // Log a message to the console
    if (event.target.classList.contains("btn") && event.target.classList.contains("btn-primary")) {
      // Get the ID of the clicked button
      const buttonId = event.target.id;
  
      // Get a reference to the table row that contains the button
      const tableRow = event.target.closest("tr");
  
      // Get the values of the input fields in the table row
      const midweek = tableRow.querySelector('input[name="midweek"]').value;
      const weekend = tableRow.querySelector('input[name="weekend"]').value;
  
      // Send an HTTP request to update the corresponding row in the database
      const xhr = new XMLHttpRequest();
      xhr.open("PUT", "/lokal/" + buttonId);
      xhr.setRequestHeader("Content-Type", "application/json");
      xhr.onreadystatechange = function() {
        if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 200) {
          // Create a new div element with the success message
          const messageDiv = document.createElement("div");
          messageDiv.classList.add("text-success");
          messageDiv.textContent = "Record updated successfully!";
  
          // Append the message div to the table row
          tableRow.appendChild(messageDiv);
        } else if (xhr.readyState === XMLHttpRequest.DONE && xhr.status === 404) {
          // Create a new div element with the error message
          const messageDiv = document.createElement("div");
          messageDiv.classList.add("text-danger");
          messageDiv.textContent = "Record not found!";
  
          // Append the message div to the table row
          tableRow.appendChild(messageDiv);
        }
      };
      xhr.send(JSON.stringify({ midweek, weekend }));
    }
  }
  