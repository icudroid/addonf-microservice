import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CustomerTarget } from './customer-target.model';
import { CustomerTargetPopupService } from './customer-target-popup.service';
import { CustomerTargetService } from './customer-target.service';

@Component({
    selector: 'jhi-customer-target-delete-dialog',
    templateUrl: './customer-target-delete-dialog.component.html'
})
export class CustomerTargetDeleteDialogComponent {

    customerTarget: CustomerTarget;

    constructor(
        private customerTargetService: CustomerTargetService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.customerTargetService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'customerTargetListModification',
                content: 'Deleted an customerTarget'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-customer-target-delete-popup',
    template: ''
})
export class CustomerTargetDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerTargetPopupService: CustomerTargetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.customerTargetPopupService
                .open(CustomerTargetDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
