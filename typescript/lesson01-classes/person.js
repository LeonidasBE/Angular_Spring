"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
exports.Person = void 0;
var Person = /** @class */ (function () {
    function Person(name) {
        this.name = name;
    }
    Person.prototype.showAge = function (age) {
        console.log(this.name + " has " + age + " years old");
    };
    Person.prototype.toString = function () {
        return "Class person created, " + this.name;
    };
    return Person;
}());
exports.Person = Person;
//# sourceMappingURL=person.js.map