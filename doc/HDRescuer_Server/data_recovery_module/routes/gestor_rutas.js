let express = require('express');
let router = express.Router();
let UsersData = require('../models/user_data')
const multer = require('multer')
const upload = multer({dest:'tmp/csv/'});

let user_data_recovery_controller = require('../controllers/user_data_recovery_controller');






router.post('/data', user_data_recovery_controller.add_data_to_db);
router.post('/ticwatchfile', upload.single('ticwatchCSV'), user_data_recovery_controller.parse_ticwatch_csv);
router.post('/empaticafile', upload.single('empaticaCSV'), user_data_recovery_controller.parse_empatica_csv);
router.post('/healthboardfile', upload.single('healthboardCSV'), user_data_recovery_controller.parse_healthboard_csv);
router.post('/deletedata',user_data_recovery_controller.delete_session_data_from_db);

module.exports = router;