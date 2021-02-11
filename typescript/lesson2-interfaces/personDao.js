"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.PersonDao = void 0;
var person_1 = require("./../lesson01/person");
var PersonDao = /** @class */ (function () {
    function PersonDao() {
    }
    PersonDao.prototype.insert = function (person) {
        console.log('Inserted succesfully', person.toString());
        return true;
    };
    PersonDao.prototype.update = function (person) {
        return true;
    };
    PersonDao.prototype.delete = function (id) {
        return true;
    };
    PersonDao.prototype.find = function (id) {
        return null;
    };
    PersonDao.prototype.findAll = function () {
        return [new person_1.Person('Leonidas')];
    };
    return PersonDao;
}());
exports.PersonDao = PersonDao;
//# sourceMappingURL=personDao.js.map