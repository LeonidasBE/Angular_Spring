import { Dao } from './dao';
import { DaoInterface } from './dao.interface';
import { Person } from '../lesson01/person';

let dao: DaoInterface<Person> = new Dao<Person>();
let person: Person = new Person("leonidas")


dao.insert(person)

