var exec = require('cordova/exec');

module.exports.coolMethod = function (arg0, success, error) {
    exec(success, error, 'HardwareInfo', 'coolMethod', [arg0]);
};

module.exports.CPUInfo = function (arg0, success, error) {
    exec(success, error, 'HardwareInfo', 'CPUInfo', [arg0]);
};

module.exports.RAMInfo = function (arg0, success, error) {
    exec(success, error, 'HardwareInfo', 'RAMInfo', [arg0]);
};

module.exports.DeviceInfo = function (arg0, success, error) {
    exec(success, error, 'HardwareInfo', 'DeviceInfo', [arg0]);
};

module.exports.DeviceName = function (arg0, success, error) {
    exec(success, error, 'HardwareInfo', 'DeviceName', [arg0]);
};
