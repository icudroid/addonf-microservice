import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AdRule } from './ad-rule.model';
import { AdRulePopupService } from './ad-rule-popup.service';
import { AdRuleService } from './ad-rule.service';
import { Ad, AdService } from '../ad';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-ad-rule-dialog',
    templateUrl: './ad-rule-dialog.component.html'
})
export class AdRuleDialogComponent implements OnInit {

    adRule: AdRule;
    isSaving: boolean;

    ads: Ad[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private adRuleService: AdRuleService,
        private adService: AdService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.adService.query()
            .subscribe((res: ResponseWrapper) => { this.ads = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.adRule.id !== undefined) {
            this.subscribeToSaveResponse(
                this.adRuleService.update(this.adRule));
        } else {
            this.subscribeToSaveResponse(
                this.adRuleService.create(this.adRule));
        }
    }

    private subscribeToSaveResponse(result: Observable<AdRule>) {
        result.subscribe((res: AdRule) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AdRule) {
        this.eventManager.broadcast({ name: 'adRuleListModification', content: 'OK'});
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
}

@Component({
    selector: 'jhi-ad-rule-popup',
    template: ''
})
export class AdRulePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private adRulePopupService: AdRulePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.adRulePopupService
                    .open(AdRuleDialogComponent as Component, params['id']);
            } else {
                this.adRulePopupService
                    .open(AdRuleDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
