var express = require('express');
var router = express.Router();



var user_controller = require('../controllers/user_controller');



router.get('/', user_controller.get_users);
router.get('/userdetails/:user_id',user_controller.get_user_details);

router.post('/newuser', user_controller.create_user);
router.post('/updateuser', user_controller.update_user);


module.exports = router;