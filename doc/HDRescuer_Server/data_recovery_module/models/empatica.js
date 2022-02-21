let mongoose = require('mongoose');
mongoose.set('debug', true);


//Definimos el Schema
let Schema = mongoose.Schema; //Importamos la clase "Schema" de mongoose, y la llamamos Schema también

let dataSchema = new Schema({
//Atributos de identificación
session_id : {type: Number, requited: true}, //Identificador sesión
timeStamp : {type: Date, required: true},
e4_accx: {type: Number},
e4_accy: {type: Number},
e4_accz: {type: Number},
e4_bvp: {type: Number},
e4_hr: {type: Number},
e4_gsr: {type: Number},
e4_ibi: {type: Number},
e4_temp:{type: Number}
},
{collection : 'Empatica'});




module.exports = mongoose.model('Empatica', dataSchema);

