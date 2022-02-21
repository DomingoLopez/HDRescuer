let Users = require('../models/user')
let bcrypt = require('bcrypt');
let dateConverter = require('../common/date_converter');
let validator = require('express-validator');
let jwt = require('jsonwebtoken')




//Devuelve un array de usuarios con unos pocos parámetros,
//para mostrarlos en la lista de la aplicación. Solo role de admin
exports.get_users = function(req, res, next) {


    Users.find().exec(function(err, users_encontrados){
        if(err) {
            res.status(404).json({});
        }else{
    
                  let users = [];
                  let user = {};
                  for(var i = 0; i< users_encontrados.length; i++){

                                user['user_id'] = users_encontrados[i].user_id;
                                user['username'] = users_encontrados[i].username;
                                user['lastname'] = users_encontrados[i].lastname;
                                user['createdAt'] = users_encontrados[i].createdAt;
                                users.push(user);
                                user = {};
                  }

                 res.status(200).json(users);
                }
        });       

};  

//Devuelve todos los datos del usuario modificables 
//No se devuelve la pass. Simplemente se deja en la aplicación el campo Nueva contraseña en blanco para reestablecerla
exports.get_user_details = function(req, res, next) {
   
    let user_id = req.params['user_id'];


    Users.find({"user_id":user_id}).exec(function(err,user){

        if(err)
            res.status(404).json({});
        else{
            if(user != null){
                //TODO: Falta no devolver la contraseña, setear bien la fecha de creación, etc
                let user_final = {
                    user_id : user[0].user_id,
                    username: user[0].username,
                    lastname: user[0].lastname,
                    email: user[0].email,
                    gender: user[0].gender,
                    age: user[0].age,
                    height: user[0].height,
                    weight: user[0].weight,
                    phone: user[0].phone,
                    phone2: user[0].phone2,
                    city: user[0].city,
                    address: user[0].address,
                    cp: user[0].cp,
                    createdAt: user[0].createdAt
                }

                res.status(200).json(user_final);
                
            }else{
                res.status(404).json("Usuario no encontrado");
            }

            
            
        }
    });
    
    



};  

//Solo disponible para el rol de admin
exports.create_user = function(req, res, next) {

let user_id = req.body.user_id;
let username = req.body.username;
let lastname = req.body.lastname;
let email = req.body.email;
let gender = req.body.gender;
let age = req.body.age;
let height = req.body.height;
let weight = req.body.weight;
let phone = req.body.phone;
let phone2 = req.body.phone2;
let city = req.body.city;
let address = req.body.address;
let cp = req.body.cp;
let createdAt = req.body.createdAt;



    let instancia = new Users({

        user_id: user_id,
        username: username,
        lastname : lastname,
        email : email,
        gender: gender,
        age: age,
        height: height,
        weight: weight,
        phone: phone,
        phone2: phone2,
        city: city,
        address: address,
        cp: cp,
        createdAt: createdAt
    });

    instancia.save(function(err, user){

        if(err){
            res.status(404).json("Error al crear el usuario");
        }else{
            //Devolvemos el usuario creado
            let user_devuelto = {
                "user_id": user.user_id,
                "username": user.username,
                "lastname": user.lastname,
                "createdAt": user.createdAt 
            }

            res.status(200).json(user_devuelto);
        
        }
        
    });



};  


//Actualiza los datos del usuario solo desde role de admin
exports.update_user = function(req, res, next) {

    let user_id = req.body.user_id;
    let username = req.body.username;
    let lastname = req.body.lastname;
    let email = req.body.email;
    let gender = req.body.gender;
    let age = req.body.age;
    let height = req.body.height;
    let weight = req.body.weight;
    let phone = req.body.phone;
    let phone2 = req.body.phone2;
    let city = req.body.city;
    let address = req.body.address;
    let cp = req.body.cp;

  

            Users.findOneAndUpdate({'user_id': user_id}, {
                username: username,
                lastname: lastname,
                email:email,
                gender:gender,
                age:age,
                height:height,
                weight:weight,
                phone:phone,
                phone2:phone2,
                city:city,
                address:address,
                cp:cp                
            },
            {new:true},
            function(err, modified_user){

                if(err){
                    res.status(404).json("Error al modificar el usuario");
                }else{
                    if(modified_user != null){

                    
                        res.status(200).json("Usuario modificado de forma satisfactoria");
                
                    }
                }

            });


}; 




//Checkea si el server está up

exports.check_server_up = function(req,res,next){


    res.status(200).json("OK");



};