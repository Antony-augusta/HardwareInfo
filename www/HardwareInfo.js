var exec = require('cordova/exec');

module.exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'HardwareInfo', 'coolMethod', [arg0]);
};

module.exports.CPUInfo = function (arg0, success, error) {
    exec(success, error, 'HardwareInfo', 'CPUInfo', [arg0]);
};
