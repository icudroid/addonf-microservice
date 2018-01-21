import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MediaTarget } from './media-target.model';
import { MediaTargetPopupService } from './media-target-popup.service';
import { MediaTargetService } from './media-target.service';
import { Brand, BrandService } from '../brand';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-media-target-dialog',
    templateUrl: './media-target-dialog.component.html'
})
export class MediaTargetDialogComponent implements OnInit {

    mediaTarget: MediaTarget;
    isSaving: boolean;

    brands: Brand[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private mediaTargetService: MediaTargetService,
        private brandService: BrandService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.brandService.query()
            .subscribe((res: ResponseWrapper) => { this.brands = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.mediaTarget.id !== undefined) {
            this.subscribeToSaveResponse(
                this.mediaTargetService.update(this.mediaTarget));
        } else {
            this.subscribeToSaveResponse(
                this.mediaTargetService.create(this.mediaTarget));
        }
    }

    private subscribeToSaveResponse(result: Observable<MediaTarget>) {
        result.subscribe((res: MediaTarget) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MediaTarget) {
        this.eventManager.broadcast({ name: 'mediaTargetListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackBrandById(index: number, item: Brand) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-media-target-popup',
    template: ''
})
export class MediaTargetPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mediaTargetPopupService: MediaTargetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.mediaTargetPopupService
                    .open(MediaTargetDialogComponent as Component, params['id']);
            } else {
                this.mediaTargetPopupService
                    .open(MediaTargetDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
