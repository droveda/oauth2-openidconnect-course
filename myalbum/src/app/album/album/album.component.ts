import { Component, OnInit } from '@angular/core';
import { PhotosService } from '../../photos.service';

@Component({
  selector: 'app-album',
  templateUrl: './album.component.html',
  styleUrls: ['./album.component.css']
})
export class AlbumComponent implements OnInit {

  // response and error returned by the Resource API call
  response:any = null
  error:any = null

  constructor(private photosSrv:PhotosService) { }

  ngOnInit(): void {

    console.log("> Calling AlbumComponent.ngOnInit()...")

    // Initiate a request to get albums
    this.photosSrv.retrieveAlbumsPromise().then(

      (resp: any) => {
        this.response = resp
        console.log(this.response)
      },
      (error:any) => {
        this.error = error
        console.log(this.error)
      }
    )

    // Note that albums may not have been retrieved as yet
    // since the above call is asynchronous
  }



}
