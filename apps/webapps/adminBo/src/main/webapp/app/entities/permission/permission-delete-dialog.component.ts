import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Permission } from './permission.model';
import { PermissionPopupService } from './permission-popup.service';
import { PermissionService } from './permission.service';

@Component({
    selector: 'jhi-permission-delete-dialog',
    templateUrl: './permission-delete-dialog.component.html'
})
export class PermissionDeleteDialogComponent {

    permission: Permission;

    constructor(
        private permissionService: PermissionService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.permissionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'permissionListModification',
                content: 'Deleted an permission'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-permission-delete-popup',
    template: ''
})
export class PermissionDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private permissionPopupService: PermissionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.permissionPopupService
                .open(PermissionDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
