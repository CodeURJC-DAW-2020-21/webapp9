import { Component, ElementRef, OnInit, ViewChild } from '@angular/core';
import { Router } from '@angular/router';
import { EventService } from 'src/app/services/event.service';
import { LoginService } from 'src/app/services/login.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-event-creator',
  templateUrl: './event-creator.component.html',
  styleUrls: ['./event-creator.component.css']
})






export class EventCreatorComponent implements OnInit {

  file: File | undefined;
  file1: File | undefined;
  file2: File | undefined;
  file3: File | undefined;
  @ViewChild("foto", {
    read: ElementRef
  }) foto: ElementRef | undefined;

  @ViewChild("foto1", {
    read: ElementRef
  }) foto1: ElementRef | undefined;

  @ViewChild("foto2", {
    read: ElementRef
  }) foto2: ElementRef | undefined;

  @ViewChild("foto3", {
    read: ElementRef
  }) foto3: ElementRef | undefined;

  constructor(private uService: UserService, private router: Router, private lService: LoginService, private eService: EventService) { }

  ngOnInit(): void {
    if (!this.lService.isAdmin()) {
      this.router.navigate(["login"]);
    }
  }

  postEvent(event: any, name: string, description: string, date: string, labels: string) {
    event.preventDefault();
    let archivos = this.foto?.nativeElement.files;
    this.file = archivos.item(0);
    let archivos1 = this.foto1?.nativeElement.files;
    this.file1 = archivos1.item(0);
    let archivos2 = this.foto2?.nativeElement.files;
    this.file2 = archivos2.item(0);
    let archivos3 = this.foto3?.nativeElement.files;
    this.file3 = archivos3.item(0);
    let lav = labels.split("/");
    this.eService.postEvent({
      name: name,
      description: description,
      date: date,
      lavels: lav
    }
    ).subscribe(
      (respones) => alert("Evento creado"),
      (error) => alert("El evento no se ha podido crear")
    );
    if (this.file != undefined) {
      this.eService.postImage(name, 1, this.file).subscribe(
        (respones) => alert("Imagen de baner posteada"),
        (error) => alert("El evento no se ha podido crear")
      );
    }
    if (this.file1 != undefined) {
      this.eService.postImage(name, 2, this.file1).subscribe(
        (respones) => alert("Primera imagen de galeria posteada"),
        (error) => alert("El evento no se ha podido crear")
      );
    }
    if (this.file2 != undefined) {
      this.eService.postImage(name, 3, this.file2).subscribe(
        (respones) => alert("Segunda imagen de galeria posteada"),
        (error) => alert("El evento no se ha podido crear")

      );
    }
    if (this.file3 != undefined) {
      this.eService.postImage(name, 4, this.file3).subscribe(
        (respones) => alert("Tercera imagen de galeria posteada"),
        (error) => alert("El evento no se ha podido crear")
      );
    }
  }
}
