let express = require('express');
let router = express.Router();
let SessionData = require('../models/session_data')


let session_data_controller = require('../controllers/session_data_controller');


//obtiene la última sesión del usuario
router.get('/user_last_session/:id',session_data_controller.get_user_last_session)
//obtiene todas las sesiones ordenadas por timestamp_ini descendente
router.get('/',session_data_controller.get_sessions)
//obtiene los datos de todas las sesiones del usuario ordenadas desc
router.get('/user/:id',session_data_controller.get_user_sessions);
//obtiene los datos de una sesión específica de un usuario
router.get('/session/:session_id',session_data_controller.get_user_session)
//Inicia la sesión pasando en el body el user_id , la hora de inicio, etc. Devuelve el session_id de sesión, que almacena para poder pararla posteriormente 
router.post('/user/init', session_data_controller.init_session);
//Para la sesión seleccionada en el body
router.post('/stop',session_data_controller.stop_session);
//Crea una sesión completa que se ha registrado en la app de forma local
router.post('/createfromlocal',session_data_controller.createfromlocal);
//Borra una sesión 
router.post('/deleteone',session_data_controller.delete_one);

module.exports = router; 