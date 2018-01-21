import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AdCampaing } from './ad-campaing.model';
import { AdCampaingPopupService } from './ad-campaing-popup.service';
import { AdCampaingService } from './ad-campaing.service';
import { Brand, BrandService } from '../brand';
import { Sector, SectorService } from '../sector';
import { Agency, AgencyService } from '../agency';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ad-campaing-dialog',
    templateUrl: './ad-campaing-dialog.component.html'
})
export class AdCampaingDialogComponent implements OnInit {

    adCampaing: AdCampaing;
    isSaving: boolean;

    brands: Brand[];

    sectors: Sector[];

    agencies: Agency[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private adCampaingService: AdCampaingService,
        private brandService: BrandService,
        private sectorService: SectorService,
        private agencyService: AgencyService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.brandService.query()
            .subscribe((res: ResponseWrapper) => { this.brands = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.sectorService.query()
            .subscribe((res: ResponseWrapper) => { this.sectors = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.agencyService.query()
            .subscribe((res: ResponseWrapper) => { this.agencies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.adCampaing.id !== undefined) {
            this.subscribeToSaveResponse(
                this.adCampaingService.update(this.adCampaing));
        } else {
            this.subscribeToSaveResponse(
                this.adCampaingService.create(this.adCampaing));
        }
    }

    private subscribeToSaveResponse(result: Observable<AdCampaing>) {
        result.subscribe((res: AdCampaing) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AdCampaing) {
        this.eventManager.broadcast({ name: 'adCampaingListModification', content: 'OK'});
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

    trackSectorById(index: number, item: Sector) {
        return item.id;
    }

    trackAgencyById(index: number, item: Agency) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-ad-campaing-popup',
    template: ''
})
export class AdCampaingPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private adCampaingPopupService: AdCampaingPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.adCampaingPopupService
                    .open(AdCampaingDialogComponent as Component, params['id']);
            } else {
                this.adCampaingPopupService
                    .open(AdCampaingDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
