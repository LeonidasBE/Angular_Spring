"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
var personDao_1 = require("./personDao");
var person_1 = require("../lesson01/person");
var personDao = new personDao_1.PersonDao();
var person = new person_1.Person('Joao');
personDao.insert(person);
//# sourceMappingURL=main.js.map