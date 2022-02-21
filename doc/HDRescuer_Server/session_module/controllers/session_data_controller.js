let SessionData = require('../models/session_data')
let dateConverter = require('../common/date_converter');



exports.get_user_last_session = function(req, res, next) {

    //Obtenemos id del usuario
    let id = req.params['id'];

    SessionData.findOne({"user_id":id,  "total_time": { $gt: 0}}).sort({"timestamp_ini" : -1}).limit(1).exec(function(err, sesion_encontrada){
        if(err) {return next(err);}

         res.status(200).json(sesion_encontrada);
        });       

};  



exports.get_sessions = function(req, res, next) {

   
    SessionData.find({"total_time": {$gt: 0}}).sort({"timestamp_ini" : -1}).exec(function(err, sesiones_encontradas){
        if(err) {return next(err);}

         res.status(200).json(sesiones_encontradas);
        });       

};  




exports.get_user_sessions = function(req, res, next) {

    let id = req.params['id'];
    

    SessionData.find({"user_id":id}).sort({"timestamp_ini:":-1}).exec(function(err,sessions){

        if(err)
            console.log("error");
        else{
            if(sessions.length != 0){

                res.status(200).json(sessions);
                
            }else{
                res.status(404).json("No hay sesiones registradas para dicho usuario");
            }            
            
        }
    });
    
    



};  


exports.get_user_session = function(req, res, next) {

    let session_id = req.params['session_id'];

    SessionData.findOne({"session_id":session_id}).exec(function(err,session){

        if(err)
            console.log("error");
        else{
            if(session != null){

                res.status(200).json(session);
                
            }else{
                res.status(404).json("No existe dicha sesión");
            }            
            
        }
    });


};  


exports.init_session = function(req,res,next){

    let session_id = req.body.session_id;
    let user_id = req.body.user_id;
    let date_ini = req.body.timestamp_ini;
    let e4band = req.body.e4band;
    let ticwatch = req.body.ticwatch;
    let ehealthboard = req.body.ehealthboard;
    let description = req.body.description;

    let instancia = new SessionData({

        session_id: session_id,
        user_id: user_id,
        timestamp_ini:date_ini,
        timestamp_fin: date_ini, //Le ponemos el mismo, hasta que no la termine no se termina la sesión
        total_time : 0,
        e4band : e4band,
        ticwatch: ticwatch,
        ehealthboard: ehealthboard,
        description: description
    });


    instancia.save(function(err, session){

        if(err){
            res.status(404).json("Error al crear la sesión");
        }else{
           //Devolvemos la sesión creada, con su id

            res.status(200).json(session.session_id);
        }
        
    });

   

};


exports.stop_session = function(req,res,next){


    let id = req.body.session_id;
    let timestamp_fin = new Date(req.body.timestamp_fin);

    //Buscamos la sesión, para hacer el cálculo del tiempo empleado
    SessionData.findOne({"session_id":id}).exec(function(err,session){

        if(err)
            res.status(404).json("Sesión no encontrada, no se ha podido registrar la parada");
        else{

            if(session != null){
                console.log(session);
                let timestamp_ini = session.timestamp_ini;
                let total_time = Math.round((timestamp_fin.getTime() - timestamp_ini.getTime())/1000); //Tiempo total en segundos

                //si todo ha ido bien
                SessionData.findOneAndUpdate({'session_id': session.session_id}, {
                    timestamp_fin : timestamp_fin,
                    total_time : total_time           
                },
                {new:true},
                function(err, modified_session){
            
                    if(err){
                        res.status(404).json("Error al finalizar la sesión, no se ha podido registrar la parada");
                    }else{
                        if(modified_session != null){
            
                            res.status(200).json(modified_session.session_id);
                    
                        }
                    }
            
                });

            }
        }


    });

   
   

};


exports.createfromlocal = function(req,res,next){


    let session_id = req.body.session_id;
    let user_id = req.body.user_id;
    let date_ini = req.body.timestamp_ini;
    let total_time = req.body.total_time;
    let e4band = req.body.e4band;
    let ticwatch = req.body.ticwatch;
    let ehealthboard = req.body.ehealthboard;
    let description = req.body.description;

    let instancia = new SessionData({

        session_id: session_id,
        user_id: user_id,
        timestamp_ini:date_ini,
        timestamp_fin: date_ini,
        total_time : total_time,
        e4band : e4band,
        ticwatch: ticwatch,
        ehealthboard: ehealthboard,
        description: description
    });

    //Primero borramos la sesión que pudiera estar almacenada
    SessionData.deleteOne({"session_id":session_id},function(err){

        if(err){
            res.status(404).json("Error al crear la sesión");
        }else{

            instancia.save(function(err, session){

                if(err){
                    res.status(404).json("Error al crear la sesión");
                }else{
                   //Devolvemos el id de sesión creada
                    res.status(200).json(session.session_id);
                }
                
            });
        }

    });



};



exports.delete_one = function(req,res,next){

    let session_id = req.body.session_id

    SessionData.deleteOne({session_id: session_id}, function(err,result){

        if(err)
        res.status(400).json("No se pudo borrar la sesión")
        else
        res.status(200).json("OK");


    });


};