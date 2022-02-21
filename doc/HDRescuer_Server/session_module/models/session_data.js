let mongoose = require('mongoose');
mongoose.set('debug', true);
//Importamos la clase "Schema" de mongoose
let Schema = mongoose.Schema; 

let sessionSchema = new Schema({

session_id:     {type: Number, required:true},
user_id:        {type: Number, required:true},
timestamp_ini : {type: Date, required: true},
timestamp_fin:  {type:Date},
total_time:     {type: Number}, 
e4band:         {type:Boolean},
ticwatch:       {type:Boolean},
ehealthboard:   {type: Boolean},
description:    {type:String}

},
//Nombre de la colección
{collection : 'SessionData'});

module.exports = mongoose.model('SessionData', sessionSchema);



