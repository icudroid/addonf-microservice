import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Ad } from './ad.model';
import { AdPopupService } from './ad-popup.service';
import { AdService } from './ad.service';
import { AdCampaing, AdCampaingService } from '../ad-campaing';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ad-dialog',
    templateUrl: './ad-dialog.component.html'
})
export class AdDialogComponent implements OnInit {

    ad: Ad;
    isSaving: boolean;

    adcampaings: AdCampaing[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private adService: AdService,
        private adCampaingService: AdCampaingService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.adCampaingService.query()
            .subscribe((res: ResponseWrapper) => { this.adcampaings = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.ad.id !== undefined) {
            this.subscribeToSaveResponse(
                this.adService.update(this.ad));
        } else {
            this.subscribeToSaveResponse(
                this.adService.create(this.ad));
        }
    }

    private subscribeToSaveResponse(result: Observable<Ad>) {
        result.subscribe((res: Ad) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Ad) {
        this.eventManager.broadcast({ name: 'adListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAdCampaingById(index: number, item: AdCampaing) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ad-popup',
    template: ''
})
export class AdPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private adPopupService: AdPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.adPopupService
                    .open(AdDialogComponent as Component, params['id']);
            } else {
                this.adPopupService
                    .open(AdDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
