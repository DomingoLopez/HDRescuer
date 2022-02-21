let Empatica = require('../models/empatica')
let TicWatch = require('../models/ticwatch')
let EHealthBoard = require('../models/ehealthboard')
const csv = require('fast-csv')

const fs = require('fs')




exports.add_data_to_db = function(req, res, next) {

    //Si no viaja ningún usuario registrado ó el usuario 0 (Valor por defecto para cuando no hay usuario), no guardamos nada
    if(req.body.session_id == null || req.body.session_id == 0){
        res.send("No está viajando el identificador de sesión");
    }else{

        let session_id = req.body.session_id;
        let timeStamp = req.body.timeStamp;
        let e4connected = req.body.e4connected;
        let ticwatchconnected = req.body.ticwatchconnected;
        let ehealthboardconnected = req.body.ehealthboardconnected;

        if(e4connected){
            let instancia_e4 = new Empatica({
                session_id: session_id,
                timeStamp: timeStamp,
                e4_accx: req.body.e4_accx,
                e4_accy: req.body.e4_accy,
                e4_accz: req.body.e4_accz,
                e4_bvp:  req.body.e4_bvp,
                e4_hr:   req.body.e4_hr,
                e4_gsr:  req.body.e4_gsr,
                e4_ibi:  req.body.e4_ibi,
                e4_temp: req.body.e4_temp
            });
            instancia_e4.save(function(err){
                if(err) return console.log(err);
            });
        }

        if(ticwatchconnected){
            let instancia_tic = new TicWatch({
                session_id: session_id,
                timeStamp: timeStamp,
                tic_accx:  req.body.tic_accx,
                tic_accy:  req.body.tic_accy,
                tic_accz:  req.body.tic_accz,
                tic_acclx: req.body.tic_acclx,
                tic_accly: req.body.tic_accly,
                tic_acclz: req.body.tic_acclz,
                tic_girx:  req.body.tic_girx,
                tic_giry:  req.body.tic_giry,
                tic_girz:  req.body.tic_girz,
                tic_hrppg: req.body.tic_hrppg,
                tic_step:  req.body.tic_step
                });
            instancia_tic.save(function(err){
                if(err) return console.log(err);
            });
        }

        if(ehealthboardconnected){
            let instancia_hb = new EHealthBoard({
                session_id: session_id,
                timeStamp: timeStamp,
                ehb_bpm: req.body.ehb_bpm,
                ehb_o2: req.body.ehb_o2,
                ehb_air: req.body.ehb_air
            });
            instancia_hb.save(function(err){
                if(err) return console.log(err);
            });
        }


        
        res.status(200).json("Datos recibidos");
    }



};  


exports.parse_ticwatch_csv = function(req,res,next){

    const filesRows = [];

    csv.parseFile(req.file.path)
        .on("data", function(data){
            filesRows.push(data);
        })
        .on("end", function(){
            
            fs.unlinkSync(req.file.path);

            let session_id = filesRows[0][0];
            //Cabeceras TICWATCH
            //"SESSION_ID,TIMESTAMP,TIC_ACCX,TIC_ACCY,TIC_ACCZ,TIC_ACCLX,TIC_ACCLY,TIC_ACCLZ,TIC_GIRX,TIC_GIRY,TIC_GIRZ,TIC_HRPPG,TIC_STEP"
            let objs = filesRows.map(function(x){

            

                let instancia_tic = new TicWatch({
                    session_id: x[0],
                    timeStamp: new Date(x[1]).toISOString(),
                    tic_accx:  x[2],
                    tic_accy:  x[3],
                    tic_accz:  x[4],
                    tic_acclx: x[5],
                    tic_accly: x[6],
                    tic_acclz: x[7],
                    tic_girx:  x[8],
                    tic_giry:  x[9],
                    tic_girz:  x[10],
                    tic_hrppg: x[11],
                    tic_step:  x[12],               
                });

                return instancia_tic;                
            });

            
           TicWatch.deleteMany({"session_id":session_id},function(err,borradas){

                if(err)
                    console.log(err);
                else{

                    TicWatch.insertMany(objs,function(err,insertados){
                        if(err)
                          console.log(err);
                     });
                }
           });

        });


    res.status(200).json("Datos recibidos");

};


exports.parse_empatica_csv = function(req,res,next){

    const filesRows = [];

    csv.parseFile(req.file.path)
        .on("data", function(data){
            filesRows.push(data);
        })
        .on("end", function(){

            fs.unlinkSync(req.file.path);

            let session_id = filesRows[0][0];
            //CABECERAS EMPATICA
            //"SESSION_ID,TIMESTAMP,E4_ACCX,E4_ACCY,E4_ACCZ,E4_BVP,E4_HR,E4_GSR,E4_IBI,E4_TEMP"
            let objs = filesRows.map(function(x){

                let instancia_emp = new Empatica({
                    session_id: x[0],
                    timeStamp: new Date(x[1]).toISOString(),
                    e4_accx:    x[2],
                    e4_accy:    x[3],
                    e4_accz:    x[4],
                    e4_bvp:     x[5],
                    e4_hr:      x[6],
                    e4_gsr:     x[7],
                    e4_ibi:     x[8],
                    e4_temp:    x[9]   
                });

                return instancia_emp;                
            });


            Empatica.deleteMany({"session_id":session_id},function(err,borradas){

                if(err)
                    console.log(err);
                else{

                    Empatica.insertMany(objs,function(err,insertados){
                        if(err)
                          console.log(err);
                     });
                }
           });

        });

    res.status(200).json("Datos recibidos");

};


exports.parse_healthboard_csv = function(req,res,next){
    const filesRows = [];
    csv.parseFile(req.file.path)
        .on("data", function(data){
            filesRows.push(data);
        })
        .on("end", function(){
            console.log(filesRows);
            fs.unlinkSync(req.file.path);

            let session_id = filesRows[0][0];
            //"SESSION_ID,TIMESTAMP,EHB_BPM,EHB_OX_BLOOD,EHB_AIR_FLOW
            let objs = filesRows.map(function(x){

                let instancia_ehb = new EHealthBoard({
                    session_id: x[0],
                    timeStamp: new Date(x[1]).toISOString(),
                    ehb_bpm:    x[2],
                    ehb_o2:     x[3],
                    ehb_air:    x[4]
                });

                return instancia_ehb;                
            });
          
            EHealthBoard.deleteMany({"session_id":session_id},function(err,borradas){

                if(err)
                    console.log(err);
                else{

                    EHealthBoard.insertMany(objs,function(err,insertados){
                        if(err)
                          console.log(err);
                     });
                }
           });


        });
    res.status(200).json("Datos HealthBoardCSV recibidos");
};




//Borrar datos de sesión

exports.delete_session_data_from_db = function(req,res,next){

    let session_id = req.body.session_id;

    // UsersData.deleteMany({session_id: session_id},function(err,result){

    //     if(err)
    //         res.status(400).json("No se pudo borrar los datos de la sesión")
    //     else
    //         res.status(200).json("OK");
    // });

    Empatica.deleteMany({session_id: session_id},function(err,result){
        if(err)
            res.status(400).json("No se pudo borrar los datos de la sesión Empatica")
    });

    TicWatch.deleteMany({session_id: session_id},function(err,result){
        if(err)
            res.status(400).json("No se pudo borrar los datos de la sesión TicWatch")
    });

    EHealthBoard.deleteMany({session_id: session_id},function(err,result){
        if(err)
            res.status(400).json("No se pudo borrar los datos de la sesión HealthBoard")
    });

    res.status(200).json("Datos Borrados");

}