import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BidCategoryMedia } from './bid-category-media.model';
import { BidCategoryMediaPopupService } from './bid-category-media-popup.service';
import { BidCategoryMediaService } from './bid-category-media.service';
import { Ad, AdService } from '../ad';
import { Category, CategoryService } from '../category';
import { Media, MediaService } from '../media';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-bid-category-media-dialog',
    templateUrl: './bid-category-media-dialog.component.html'
})
export class BidCategoryMediaDialogComponent implements OnInit {

    bidCategoryMedia: BidCategoryMedia;
    isSaving: boolean;

    ads: Ad[];

    categories: Category[];

    media: Media[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private bidCategoryMediaService: BidCategoryMediaService,
        private adService: AdService,
        private categoryService: CategoryService,
        private mediaService: MediaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.adService.query()
            .subscribe((res: ResponseWrapper) => { this.ads = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.categoryService.query()
            .subscribe((res: ResponseWrapper) => { this.categories = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.mediaService.query()
            .subscribe((res: ResponseWrapper) => { this.media = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bidCategoryMedia.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bidCategoryMediaService.update(this.bidCategoryMedia));
        } else {
            this.subscribeToSaveResponse(
                this.bidCategoryMediaService.create(this.bidCategoryMedia));
        }
    }

    private subscribeToSaveResponse(result: Observable<BidCategoryMedia>) {
        result.subscribe((res: BidCategoryMedia) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: BidCategoryMedia) {
        this.eventManager.broadcast({ name: 'bidCategoryMediaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAdById(index: number, item: Ad) {
        return item.id;
    }

    trackCategoryById(index: number, item: Category) {
        return item.id;
    }

    trackMediaById(index: number, item: Media) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-bid-category-media-popup',
    template: ''
})
export class BidCategoryMediaPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bidCategoryMediaPopupService: BidCategoryMediaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.bidCategoryMediaPopupService
                    .open(BidCategoryMediaDialogComponent as Component, params['id']);
            } else {
                this.bidCategoryMediaPopupService
                    .open(BidCategoryMediaDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
