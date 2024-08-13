import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { PhotosService } from '../../photos.service';

@Component({
  selector: 'app-photos',
  templateUrl: './photos.component.html',
  styleUrls: ['./photos.component.css']
})
export class PhotosComponent implements OnInit {

 // response and error returned by the Resource API call
  response:any = null
  error:any = null

  constructor(private actRoute:ActivatedRoute, private photosSrv:PhotosService) { }

  ngOnInit(): void {

    console.log("> Calling PhotosComponent.ngOnInit()...")

    // Initiate a request to get photos for an album
    let albumID = this.actRoute.snapshot.paramMap.get("id");
    this.photosSrv.retrievePhotosPromise(albumID).then(
        (resp: any) => {
          this.response = resp
          console.log(this.response)
        },
        (error:any) => {
          this.error = error
          console.log(this.error)
        }
    )

    // Note that photos may not have been retrieved as yet
    // since the above call is asynchronous

  }

}
