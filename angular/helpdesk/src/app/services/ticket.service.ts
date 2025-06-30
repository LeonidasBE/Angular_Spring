import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Ticket } from '../model/ticket.model';
import { HELP_DESK_API } from './helpdesk.api';
import { ResponseApi } from '../model/response-api';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TicketService {

  constructor(private http: HttpClient) { }

  createOrUpdate(ticket: Ticket): Observable<ResponseApi>{
    if(ticket.id != null && ticket.id != ''){
      return this.http.put<ResponseApi>(`${HELP_DESK_API}/api/ticket`, ticket);
    } else {
      ticket.id = null;
      ticket.status = 'New';
      return this.http.post<ResponseApi>(`${HELP_DESK_API}/api/ticket`, ticket);
    }
  }

  findAll(page: number, count: number): Observable<ResponseApi> {
    return this.http.get<ResponseApi>(`${HELP_DESK_API}/api/ticket/${page}/${count}`);
  }

  findById(id: string): Observable<ResponseApi>{
    return this.http.get<ResponseApi>(`${HELP_DESK_API}/api/ticket/${id}`);
  }

  delete(id: string): Observable<ResponseApi>{
    return this.http.delete<ResponseApi>(`${HELP_DESK_API}/api/ticket/${id}`);
  }

  findByParams(page: number, count: number, assignedToMe: boolean, t: Ticket): Observable<ResponseApi>{
    t.number = t.number ?? 0;
    t.title = t.title == '' ? 'uninformed' : t.title;
    t.status = t.status == '' ? 'uninformed' : t.status;
    t.priority = t.priority == '' ? 'uninformed' : t.priority;

    return this.http.get<ResponseApi>(`${HELP_DESK_API}/api/ticket/${page}/${count}/${t.number}/${t.title}/${t.status}/${t.priority}/${assignedToMe}`);
  }

  changeStatus(status: string, ticket: Ticket){
    return this.http.put(`${HELP_DESK_API}/api/ticket/${ticket.id}/${status}`, ticket);
  }

  summary(){
    return this.http.get(`${HELP_DESK_API}/api/ticket/summary`);
  }

}
