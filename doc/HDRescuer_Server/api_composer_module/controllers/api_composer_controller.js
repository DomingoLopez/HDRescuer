const http = require('http');

const endpoints = require('../common/endpoints');
let dateConverter = require('../common/date_converter');


//obtiene nombre de usuario y fecha última sesión y duración que se hizo
//Accede al microservicio de usuarios y sesiones
//Comprobado con postman
exports.get_users_short =  async function(req, res, next) {

    //Welcome to callback hell....
    let users =  getUsers(function(users){
        if(users.length != 0){
            let sessions = getSessions(function(sessions){

                let response = parseUserShortResponse(users,sessions);

                res.status(200).json(response);

            });
        }else{
            res.status(404).json("No hay usuarios creados")
        }
        
    });

    

};  


//Obtiene todos los datos del usuario y todos los datos de la última sesión realizada
//Comprobado con postman
exports.get_user_details = function(req, res, next) {


    let user_id = req.params['user_id'];
    
    let user =  getUser(user_id,function(user){
        if(user != null){
            let session = getSession(user_id,function(session){

                let response = parseUserCompleteSession(user,session);

                res.status(200).json(response);

            });
        }else{
            res.status(404).json("No se ha encontrado el usuario")
        }
        
    });


};  

/**
 * 
 * FUNCIONES DE PARSEO
 */

//Función que mezcla los usuarios (todos) con su última monitorización
function parseUserShortResponse(users, sessions){

    let users_short = [];
    let array_sesiones = [];
    //Las sesiones vienen ordenadas por fecha descendente, así que nos quedamos con la primera
    for(let i = 0; i<users.length; i++){

        for(let j = 0;j<sessions.length;j++)
            if(sessions[j].user_id == users[i].user_id)
                array_sesiones.push(sessions[j])
        
        let session_id = null
        let timestamp_ini = null;
        let total_time = null;

        if(array_sesiones.length > 0){
            session_id = array_sesiones[0].session_id;
            timestamp_ini = array_sesiones[0].timestamp_ini;
            total_time = array_sesiones[0].total_time;
        }

        let user_final = {
            user_id: users[i].user_id,
            username: users[i].username,
            lastname: users[i].lastname,
            createdAt: users[i].createdAt,
            //Aquí es donde incorporamos las cosas de la sesión
            session_id: session_id,
            timestamp_ini: timestamp_ini,
            total_time: total_time
        };

            users_short.push(user_final);
            user_final = null;
            array_sesiones = [];
    }

    return users_short;
   

}


function parseUserCompleteSession(user,session){

    console.log(session);
    let session_id = null;
    let timestamp_ini = null;
    let timestamp_fin = null;
    let total_time = null;
    let e4band = null;
    let ticwatch = null;
    let ehealthboard = null;
    let description = null;


    if(session!= null){

        session_id = session.session_id;
        timestamp_ini = session.timestamp_ini;
        timestamp_fin = session.timestamp_fin;
        total_time = session.total_time;
        e4band = session.e4band;
        ticwatch = session.ticwatch;
        ehealthboard = session.ehealthboard;
        description = session.description;
    }

    let user_final = {
        user_id : user.user_id,
        username: user.username,
        lastname: user.lastname,
        email: user.email,
        gender: user.gender,
        age: user.age,
        height: user.height,
        weight: user.weight,
        phone: user.phone,
        phone2: user.phone2,
        city: user.city,
        address: user.address,
        cp: user.cp,
        createdAt: user.createdAt,
        //Atributos que añadimos para completar
        session_id: session_id,
        timestamp_ini: timestamp_ini,
        timestamp_fin: timestamp_fin,
        total_time: total_time,
        e4band: e4band,
        ticwatch: ticwatch,
        ehealthboard: ehealthboard,
        description: description
    }

    return user_final;

}





/**
 * 
 * FUNCIONES CALLBACK PARA LAS PETICIONES HTTP A LOS MICROSERVICIOS
 */

//Obtiene los usuarios del módulo de usuarios
function getUsers(callback){

    let options = {
        host: endpoints.HOST_IP_USER,
        port: endpoints.USERS_PORT,
        path:'/api/users/',
        method: 'GET'
    };

    let data = '';
    let request = http.request(options, res =>{

        res.on('data', function(chunk){
            data+=chunk; 
         });

         res.on('end', function(){
             console.log("Terminada la petición");
             console.log(data);
             callback(JSON.parse(data));
             return;
         });
    });

    request.on('error', error =>{
        console.log(error);
    });

    request.end();

}


//Obtiene las sesiones del módulo de sesiones (ordenadas por timestamp_inin desc)
function getSessions(callback){

    let options = {
        host: endpoints.HOST_IP_SESSION,
        port: endpoints.SESSIONS_PORT,
        path:'/api/sessions/',
        method: 'GET'
    };

    let data = '';
    let request = http.request(options, res =>{

        res.on('data', function(chunk){
            data+=chunk; 
         });

         res.on('end', function(){
             console.log("Terminada la petición");
             callback(JSON.parse(data));
             return;
         });
    });

    request.on('error', error =>{
        console.log(error);
    });

    request.end();
}


//Obtiene los details de un usuario
function getUser(user_id, callback){
    console.log(user_id);
    let options = {
        host: endpoints.HOST_IP_USER,
        port: endpoints.USERS_PORT,
        path:'/api/users/userdetails/'+user_id,
        method: 'GET'
    };

    let data = '';
    let request = http.request(options, res =>{

        res.on('data', function(chunk){
            data+=chunk; 
         });

         res.on('end', function(){
             console.log("Terminada la petición");
             callback(JSON.parse(data));
             return;
         });
    });

    request.on('error', error =>{
        console.log(error);
    });

    request.end();

}



//Obtiene la última sesión de un usuario
function getSession(user_id, callback){

    let options = {
        host: endpoints.HOST_IP_SESSION,
        port: endpoints.SESSIONS_PORT,
        path:'/api/sessions/user_last_session/'+user_id,
        method: 'GET'
    };

    let data = '';
    let request = http.request(options, res =>{

        res.on('data', function(chunk){
            data+=chunk; 
         });

         res.on('end', function(){
             console.log("Terminada la petición");
             callback(JSON.parse(data));
             return;
         });
    });

    request.on('error', error =>{
        console.log(error);
    });

    request.end();
}