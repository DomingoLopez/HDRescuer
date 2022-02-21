var express = require('express');
var router = express.Router();


var user_controller = require('../controllers/user_controller');



router.post('/logintest', user_controller.check_server_up);

module.exports = router;