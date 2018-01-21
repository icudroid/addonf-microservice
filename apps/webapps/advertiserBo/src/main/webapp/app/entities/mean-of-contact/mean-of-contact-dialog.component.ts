import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MeanOfContact } from './mean-of-contact.model';
import { MeanOfContactPopupService } from './mean-of-contact-popup.service';
import { MeanOfContactService } from './mean-of-contact.service';
import { Contact, ContactService } from '../contact';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-mean-of-contact-dialog',
    templateUrl: './mean-of-contact-dialog.component.html'
})
export class MeanOfContactDialogComponent implements OnInit {

    meanOfContact: MeanOfContact;
    isSaving: boolean;

    contacts: Contact[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private meanOfContactService: MeanOfContactService,
        private contactService: ContactService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.contactService.query()
            .subscribe((res: ResponseWrapper) => { this.contacts = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.meanOfContact.id !== undefined) {
            this.subscribeToSaveResponse(
                this.meanOfContactService.update(this.meanOfContact));
        } else {
            this.subscribeToSaveResponse(
                this.meanOfContactService.create(this.meanOfContact));
        }
    }

    private subscribeToSaveResponse(result: Observable<MeanOfContact>) {
        result.subscribe((res: MeanOfContact) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MeanOfContact) {
        this.eventManager.broadcast({ name: 'meanOfContactListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackContactById(index: number, item: Contact) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-mean-of-contact-popup',
    template: ''
})
export class MeanOfContactPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private meanOfContactPopupService: MeanOfContactPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.meanOfContactPopupService
                    .open(MeanOfContactDialogComponent as Component, params['id']);
            } else {
                this.meanOfContactPopupService
                    .open(MeanOfContactDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
