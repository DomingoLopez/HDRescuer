let createError = require('http-errors');
let express = require('express');
let path = require('path');
let cookieParser = require('cookie-parser');
let logger = require('morgan');
let bodyParser = require('body-parser');
let validator = require('express-validator');
let cors = require('cors');
let jwt = require('jsonwebtoken');


//Declaraciones de los 'objetos' de ruta exportados para su uso
let gestor_rutas_api = require('./routes/gestor_rutas');
let gestor_rutas_login = require('./routes/gestor_rutas_login');

let app = express();
app.use(cors());


const mongoose = require('mongoose');
mongoose.connect('mongodb://mongodb/users', {useNewUrlParser: true, useUnifiedTopology: true});

const db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function() {
  console.log("Conectado a mongoDB. Modulo Usuarios");
});


app.use(logger('dev'));
app.use(express.json());
app.use(express.urlencoded({ extended: false }));
app.use(cookieParser());
app.use(express.static(path.join(__dirname, 'public')));
app.use(bodyParser.json());         // to support JSON-encoded bodies
app.use(bodyParser.urlencoded({     // to support URL-encoded bodies
    extended: true
}));




//Rutas para uso de la api
app.use('/api/users', gestor_rutas_api);
app.use('/api/auth', gestor_rutas_login);



module.exports = app;
