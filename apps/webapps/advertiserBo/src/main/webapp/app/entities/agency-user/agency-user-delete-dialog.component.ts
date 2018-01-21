import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { AgencyUser } from './agency-user.model';
import { AgencyUserPopupService } from './agency-user-popup.service';
import { AgencyUserService } from './agency-user.service';

@Component({
    selector: 'jhi-agency-user-delete-dialog',
    templateUrl: './agency-user-delete-dialog.component.html'
})
export class AgencyUserDeleteDialogComponent {

    agencyUser: AgencyUser;

    constructor(
        private agencyUserService: AgencyUserService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.agencyUserService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'agencyUserListModification',
                content: 'Deleted an agencyUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-agency-user-delete-popup',
    template: ''
})
export class AgencyUserDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private agencyUserPopupService: AgencyUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.agencyUserPopupService
                .open(AgencyUserDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
