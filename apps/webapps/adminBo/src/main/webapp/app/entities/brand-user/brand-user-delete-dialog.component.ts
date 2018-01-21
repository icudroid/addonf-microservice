import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BrandUser } from './brand-user.model';
import { BrandUserPopupService } from './brand-user-popup.service';
import { BrandUserService } from './brand-user.service';

@Component({
    selector: 'jhi-brand-user-delete-dialog',
    templateUrl: './brand-user-delete-dialog.component.html'
})
export class BrandUserDeleteDialogComponent {

    brandUser: BrandUser;

    constructor(
        private brandUserService: BrandUserService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.brandUserService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'brandUserListModification',
                content: 'Deleted an brandUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-brand-user-delete-popup',
    template: ''
})
export class BrandUserDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private brandUserPopupService: BrandUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.brandUserPopupService
                .open(BrandUserDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
