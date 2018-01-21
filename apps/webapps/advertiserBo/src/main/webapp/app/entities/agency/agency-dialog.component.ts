import { Component, OnInit, OnDestroy, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiDataUtils } from 'ng-jhipster';

import { Agency } from './agency.model';
import { AgencyPopupService } from './agency-popup.service';
import { AgencyService } from './agency.service';

@Component({
    selector: 'jhi-agency-dialog',
    templateUrl: './agency-dialog.component.html'
})
export class AgencyDialogComponent implements OnInit {

    agency: Agency;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private agencyService: AgencyService,
        private elementRef: ElementRef,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.agency, this.elementRef, field, fieldContentType, idInput);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.agency.id !== undefined) {
            this.subscribeToSaveResponse(
                this.agencyService.update(this.agency));
        } else {
            this.subscribeToSaveResponse(
                this.agencyService.create(this.agency));
        }
    }

    private subscribeToSaveResponse(result: Observable<Agency>) {
        result.subscribe((res: Agency) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Agency) {
        this.eventManager.broadcast({ name: 'agencyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-agency-popup',
    template: ''
})
export class AgencyPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private agencyPopupService: AgencyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.agencyPopupService
                    .open(AgencyDialogComponent as Component, params['id']);
            } else {
                this.agencyPopupService
                    .open(AgencyDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
