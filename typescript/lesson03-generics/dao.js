"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Dao = void 0;
var Dao = /** @class */ (function () {
    function Dao() {
    }
    Dao.prototype.insert = function (object) {
        console.log('Inserted succesfully');
        return true;
    };
    Dao.prototype.update = function (object) {
        return true;
    };
    Dao.prototype.delete = function (id) {
        return true;
    };
    Dao.prototype.find = function (id) {
        return null;
    };
    Dao.prototype.findAll = function () {
        return [null];
    };
    return Dao;
}());
exports.Dao = Dao;
//# sourceMappingURL=dao.js.map