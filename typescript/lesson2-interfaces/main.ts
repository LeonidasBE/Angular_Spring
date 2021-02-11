import { DaoInterface } from './dao.interface';
import { PersonDao } from './personDao';
import { Person } from '../lesson01/person';

let personDao : DaoInterface = new PersonDao();

let person: Person = new Person('Joao');

personDao.insert(person)