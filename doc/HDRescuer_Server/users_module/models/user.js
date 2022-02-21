var mongoose = require('mongoose');
mongoose.set('debug', true);


//Definimos el Schema
var Schema = mongoose.Schema; //Importamos la clase "Schema" de mongoose, y la llamamos Schema también


var dataSchema = new Schema({
user_id: {type: Number},
username: {type: String},
lastname : {type: String},
email : {type: String},
gender: {type: String},
age: {type: Number},
height: {type: Number},
weight: {type: Number},
phone: {type: String},
phone2: {type: String},
city: {type: String},
address: {type: String},
cp: {type: String},
createdAt: {type: Date}
},
{collection : 'Users'});




module.exports = mongoose.model('Users', dataSchema);



