import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MeanOfContact } from './mean-of-contact.model';
import { MeanOfContactPopupService } from './mean-of-contact-popup.service';
import { MeanOfContactService } from './mean-of-contact.service';

@Component({
    selector: 'jhi-mean-of-contact-delete-dialog',
    templateUrl: './mean-of-contact-delete-dialog.component.html'
})
export class MeanOfContactDeleteDialogComponent {

    meanOfContact: MeanOfContact;

    constructor(
        private meanOfContactService: MeanOfContactService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.meanOfContactService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'meanOfContactListModification',
                content: 'Deleted an meanOfContact'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-mean-of-contact-delete-popup',
    template: ''
})
export class MeanOfContactDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private meanOfContactPopupService: MeanOfContactPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.meanOfContactPopupService
                .open(MeanOfContactDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
