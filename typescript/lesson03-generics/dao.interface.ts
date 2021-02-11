export interface DaoInterface<T>{
    tableName : string

    insert(object:T):boolean
    update(object:T):boolean
    delete(id:number):boolean
    find(id:number):T
    findAll():[T]
}