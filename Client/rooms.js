//--------GET request To display all room details using rooms endpoint-------------------------------
var roomData = document.querySelector(".roomListing");
//Code Adapted from https://www.freecodecamp.org/news/how-to-make-api-calls-with-fetch/ 
async function getRooms(){
fetch('http://localhost:8080/RESTService2/webresources/Room')
.then(async response=>{
    
    if (!response.ok) {
            const errormsg = await response.json();
            throw new Error(`HTTP error! Status: ${response.status}`);
        }
        return response.json();
})
.then(function RoomListing(roomsDetailsArray){
 roomsDetailsArray.forEach(function (roomList){
        const list_holder = document.createElement('div');
        list_holder.classList.add('list_holder');
         list_holder.innerHTML = `
        <h3>RoomID: ${roomList.roomId}</h3>
        <h4>${roomList.name}</h4>
        <p>Location: ${roomList.location}</p>
        <p>Latitude: ${roomList.latitude}</p>
        <p>Longitude: ${roomList.longitude}</p>
        <p>PPM: ${roomList.price_per_month}</p>
        <p>Languages: ${roomList.spoken_lang}</p>
        <p>Availability Date: ${roomList.availability_date}</p>
        <p>Shared With: ${roomList.shared_with}</p>
        <p>Weather: ${roomList.weather.weather}</p>
        <p>Max Temp: ${roomList.weather.max} deg</p>
        <p>Min Temp: ${roomList.weather.min} deg</p>
`;
    roomData.appendChild(list_holder);
})
})
.catch(error=>{
    console.error('Error:', error.message);
            alert(`Error: ${error.message}`);
})
}
getRooms();


//-----------Search rooms using Room/searchAvailRooms------------------------
async function searchRooms(){
    fetch('http://localhost:8080/RESTService2/webresources/Room/searchAvailRooms')
    .then(async response=>{
        if (!response.ok) {
                const errormsg = await response.json();
                throw new Error(`HTTP error! Status: ${response.status}`);
            }
        return response.json();
    })
    .then(function RoomListing(roomsDetailsArray){
        roomData.innerHTML = '';
     roomsDetailsArray.forEach(function (roomList){
            const list_holder = document.createElement('div');
            list_holder.classList.add('list_holder');
            list_holder.innerHTML = `
            <h3>RoomID: ${roomList.roomId}</h3>
            <h3>${roomList.name}</h3>
            <p>Location: ${roomList.location}</p>
            <p>Latitude: ${roomList.latitude}</p>
            <p>Longitude: ${roomList.longitude}</p>
            <p>PPM: ${roomList.price_per_month}</p>
            <p>Languages: ${roomList.spoken_lang}</p>
            <p>Availability Date: ${roomList.availability_date}</p>
            <p>Shared With: ${roomList.shared_with}</p>
            <p>Weather: ${roomList.weather.weather}</p>
            <p>Max Temp: ${roomList.weather.max} deg</p>
            <p>Min Temp: ${roomList.weather.min} deg</p>
    `;
    roomData.appendChild(list_holder);
    })
    })
    .catch(error=>{
        console.error('Error:', error.message);
                alert(`Error: ${error.message}`);
    })
}

var searchtbtn = document.getElementById('search');   
searchtbtn.addEventListener('click' , searchRooms);    


//--------POST request for the application form--- /Room/applyRoom--------------------------------

const applyform = document.getElementById('applyInfo');

async function sendInfo(){
const roomId = document.getElementById('roomId').value;
const userId = document.getElementById('userId').value;

const sendData = {roomId: parseInt(roomId), userId: parseInt(userId)};

try{  // Adapted from https://developer.mozilla.org/en-US/docs/Learn_web_development/Extensions/Forms/Sending_forms_through_JavaScript
const response = await fetch('http://localhost:8080/RESTService2/webresources/Room/applyRoom',{
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        
    },
    body: JSON.stringify(sendData),
});
    if (!response.ok) {
        const errormsg = await response.json();
            throw new Error(errormsg.error||`HTTP error! Status: ${response.status}`);
        }
const data = await response.json();
alert(data.message);
}catch(error){
console.error('Error:', error.message);
        alert(`Error: ${error.message}`);
}
}
applyform.addEventListener('submit', (event)=>{
    event.preventDefault();
    sendInfo();
});

//--------POST request for the Cancellation form-----/Room/cancelRoom---------------------------
const cancelform = document.getElementById('cancelInfo');

async function sendCancelInfo(){
const croomId = document.getElementById('croomId').value;
const cuserId = document.getElementById('cuserId').value;

const sendCancelData = {roomId: parseInt(croomId), userId: parseInt(cuserId)};
try{
const response = await fetch('http://localhost:8080/RESTService2/webresources/Room/cancelRoom',{
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
    },
    body: JSON.stringify(sendCancelData),
});
    if (!response.ok) {
        const errormsg = await response.json();
            throw new Error(errormsg.error||`HTTP error! Status: ${response.status}`);
        }

const data = await response.json();
alert(data.message);

}catch(error){
console.error('Error:', error.message);
        alert(`Error: ${error.message}`);
}
}
cancelform.addEventListener('submit', (event)=>{
    event.preventDefault();
    sendCancelInfo();
});

//------------View History----/Room/viewHistory/{userId}-----------
var histform = document.getElementById('histInfo');
var histDataDisplay = document.querySelector(".histDisplay");

async function getHist(){
const huserId = document.getElementById('huserId').value; //get the user entered value
const url = `http://localhost:8080/RESTService2/webresources/Room/viewHistory/${huserId}`;  // take user id as value from users and pass as part of path parameter

fetch(url)
.then( async response=>{
    if (!response.ok) {
        const errormsg = await response.json();
           throw new Error(errormsg.error||`HTTP error! Status: ${response.status}`);
        }
   return response.json();
})

.then(function HistData(applicationDetailsArray){
    histDataDisplay.innerHTML = '';

if(applicationDetailsArray && applicationDetailsArray.length>0){
    applicationDetailsArray.forEach(function (applicationList){

        const detail_holder = document.createElement('div');
        detail_holder.classList.add('detail_holder');
        detail_holder.innerHTML = `
        <h3>RoomID: ${applicationList.roomId}</h3>
       <h3>Status: ${applicationList.status}</h3>
    `;
    histDataDisplay.appendChild(detail_holder);
     
})
}else{
    histDataDisplay.innerHTML = `<p>No data found</p>`;
}
})
.catch(error=>{
    console.error('Error:', error.message);
            alert(`Error: ${error.message}`);

});
}
histform.addEventListener('submit', (event)=>{
    event.preventDefault();
    getHist();
});

//-----------Distance calculator--------/Distance---------------------------
var distanceform = document.getElementById('distanceInfo');
var distanceDataDisplay = document.querySelector(".distanceDisplay");

async function distanceCalculator(){
    const origins = document.getElementById('origins').value;
    const destinations = document.getElementById('destinations').value;

    const EOrigins = encodeURIComponent(origins); //encode the special characters passed in the value 
    const EDestinations = encodeURIComponent(destinations);
    const CalcUrl = `http://localhost:8080/RESTService2/webresources/Distance?origins=${EOrigins}&destinations=${EDestinations}`;

fetch(CalcUrl)
.then( async response=>{
    if (!response.ok) {
        const errormsg = await response.json();
           throw new Error(`HTTP error! Status: ${response.status}`);
        }
   return response.json();
})

.then(function distanceData(distanceDetailsArray){
    distanceDataDisplay.innerHTML = '';

if(distanceDetailsArray.rows && distanceDetailsArray.rows.length>0){  //check th rows 
    distanceDetailsArray.rows.forEach(function (rows){
        
        if(rows.elements && rows.elements.length>0){   //check the elements
            rows.elements.forEach(function (elements){
            const data_holder = document.createElement('div');
            data_holder.classList.add('data_holder');
            data_holder.innerHTML = `
            
            <h3>Distance: ${elements.distance.text}</h3>
            <h3>Duration: ${elements.duration.text}</h3>
    `;
    distanceDataDisplay.appendChild(data_holder);
    })          
}    
})
}else{
    distanceDataDisplay.innerHTML = `<p>No data found</p>`;
}
})
.catch(error=>{
    console.error('Error:', error.message);
            alert(`Error: ${error.message}`);

});
}
distanceform.addEventListener('submit', (event=>{
    event.preventDefault();
    distanceCalculator()
}))




