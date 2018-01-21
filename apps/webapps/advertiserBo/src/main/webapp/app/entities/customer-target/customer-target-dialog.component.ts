import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CustomerTarget } from './customer-target.model';
import { CustomerTargetPopupService } from './customer-target-popup.service';
import { CustomerTargetService } from './customer-target.service';
import { Brand, BrandService } from '../brand';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-customer-target-dialog',
    templateUrl: './customer-target-dialog.component.html'
})
export class CustomerTargetDialogComponent implements OnInit {

    customerTarget: CustomerTarget;
    isSaving: boolean;

    brands: Brand[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private customerTargetService: CustomerTargetService,
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
        if (this.customerTarget.id !== undefined) {
            this.subscribeToSaveResponse(
                this.customerTargetService.update(this.customerTarget));
        } else {
            this.subscribeToSaveResponse(
                this.customerTargetService.create(this.customerTarget));
        }
    }

    private subscribeToSaveResponse(result: Observable<CustomerTarget>) {
        result.subscribe((res: CustomerTarget) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CustomerTarget) {
        this.eventManager.broadcast({ name: 'customerTargetListModification', content: 'OK'});
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
    selector: 'jhi-customer-target-popup',
    template: ''
})
export class CustomerTargetPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerTargetPopupService: CustomerTargetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.customerTargetPopupService
                    .open(CustomerTargetDialogComponent as Component, params['id']);
            } else {
                this.customerTargetPopupService
                    .open(CustomerTargetDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
