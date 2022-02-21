let mongoose = require('mongoose');
mongoose.set('debug', true);


//Definimos el Schema
let Schema = mongoose.Schema; //Importamos la clase "Schema" de mongoose, y la llamamos Schema también

let dataSchema = new Schema({
//Atributos de identificación
session_id : {type: Number, requited: true}, //Identificador sesión
timeStamp : {type: Date, required: true},
ehb_bpm: {type: Number},
ehb_o2: {type: Number},
ehb_air: {type: Number}
},
{collection : 'EHealthBoard'});


module.exports = mongoose.model('EHealthBoard', dataSchema);


