let mongoose = require('mongoose');
mongoose.set('debug', true);


//Definimos el Schema
let Schema = mongoose.Schema; //Importamos la clase "Schema" de mongoose, y la llamamos Schema también

let dataSchema = new Schema({
//Atributos de identificación
session_id : {type: Number, requited: true}, //Identificador sesión
timeStamp : {type: Date, required: true},
//Atributos del ticwatch
tic_accx: {type: Number},
tic_accy: {type: Number},
tic_accz: {type: Number},
tic_acclx: {type: Number},
tic_accly: {type: Number},
tic_acclz: {type: Number},
tic_girx: {type: Number},
tic_giry: {type: Number},
tic_girz: {type: Number},
tic_hrppg: {type : Number},
tic_step: {type: Number}
},
{collection : 'TicWatch'});


module.exports = mongoose.model('TicWatch', dataSchema);

