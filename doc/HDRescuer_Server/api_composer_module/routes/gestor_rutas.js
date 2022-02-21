var express = require('express');
var router = express.Router();


var api_composer_module = require('../controllers/api_composer_controller');


router.get('/get-users-short',api_composer_module.get_users_short);
router.get('/get-user-details/:user_id',api_composer_module.get_user_details);



module.exports = router; 