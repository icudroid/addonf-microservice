import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MediaUser } from './media-user.model';
import { MediaUserPopupService } from './media-user-popup.service';
import { MediaUserService } from './media-user.service';
import { Media, MediaService } from '../media';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-media-user-dialog',
    templateUrl: './media-user-dialog.component.html'
})
export class MediaUserDialogComponent implements OnInit {

    mediaUser: MediaUser;
    isSaving: boolean;

    media: Media[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mediaUserService: MediaUserService,
        private mediaService: MediaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.mediaService.query()
            .subscribe((res: ResponseWrapper) => { this.media = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mediaUser.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mediaUserService.update(this.mediaUser));
        } else {
            this.subscribeToSaveResponse(
                this.mediaUserService.create(this.mediaUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<MediaUser>) {
        result.subscribe((res: MediaUser) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MediaUser) {
        this.eventManager.broadcast({ name: 'mediaUserListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackMediaById(index: number, item: Media) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-media-user-popup',
    template: ''
})
export class MediaUserPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mediaUserPopupService: MediaUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mediaUserPopupService
                    .open(MediaUserDialogComponent as Component, params['id']);
            } else {
                this.mediaUserPopupService
                    .open(MediaUserDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
