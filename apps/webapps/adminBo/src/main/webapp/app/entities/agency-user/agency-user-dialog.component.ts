import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { AgencyUser } from './agency-user.model';
import { AgencyUserPopupService } from './agency-user-popup.service';
import { AgencyUserService } from './agency-user.service';
import { Agency, AgencyService } from '../agency';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-agency-user-dialog',
    templateUrl: './agency-user-dialog.component.html'
})
export class AgencyUserDialogComponent implements OnInit {

    agencyUser: AgencyUser;
    isSaving: boolean;

    agencies: Agency[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private agencyUserService: AgencyUserService,
        private agencyService: AgencyService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.agencyService.query()
            .subscribe((res: ResponseWrapper) => { this.agencies = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.agencyUser.id !== undefined) {
            this.subscribeToSaveResponse(
                this.agencyUserService.update(this.agencyUser));
        } else {
            this.subscribeToSaveResponse(
                this.agencyUserService.create(this.agencyUser));
        }
    }

    private subscribeToSaveResponse(result: Observable<AgencyUser>) {
        result.subscribe((res: AgencyUser) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: AgencyUser) {
        this.eventManager.broadcast({ name: 'agencyUserListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackAgencyById(index: number, item: Agency) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-agency-user-popup',
    template: ''
})
export class AgencyUserPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private agencyUserPopupService: AgencyUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.agencyUserPopupService
                    .open(AgencyUserDialogComponent as Component, params['id']);
            } else {
                this.agencyUserPopupService
                    .open(AgencyUserDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
