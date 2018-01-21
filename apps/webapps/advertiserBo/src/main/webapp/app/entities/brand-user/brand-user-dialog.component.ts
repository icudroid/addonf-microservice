import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { BrandUser } from './brand-user.model';
import { BrandUserPopupService } from './brand-user-popup.service';
import { BrandUserService } from './brand-user.service';
import { Brand, BrandService } from '../brand';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-brand-user-dialog',
    templateUrl: './brand-user-dialog.component.html'
})
export class BrandUserDialogComponent implements OnInit {

    brandUser: BrandUser;
    isSaving: boolean;

    brands: Brand[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private brandUserService: BrandUserService,
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
        if (this.brandUser.id !== undefined) {
            this.subscribeToSaveResponse(
                this.brandUserService.update(this.brandUser));
        } else {
            this.subscribeToSaveResponse(
                this.brandUserService.create(this.brandUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<BrandUser>) {
        result.subscribe((res: BrandUser) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: BrandUser) {
        this.eventManager.broadcast({ name: 'brandUserListModification', content: 'OK'});
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
    selector: 'jhi-brand-user-popup',
    template: ''
})
export class BrandUserPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private brandUserPopupService: BrandUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.brandUserPopupService
                    .open(BrandUserDialogComponent as Component, params['id']);
            } else {
                this.brandUserPopupService
                    .open(BrandUserDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
