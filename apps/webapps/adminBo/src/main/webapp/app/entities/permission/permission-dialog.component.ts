import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Permission } from './permission.model';
import { PermissionPopupService } from './permission-popup.service';
import { PermissionService } from './permission.service';

@Component({
    selector: 'jhi-permission-dialog',
    templateUrl: './permission-dialog.component.html'
})
export class PermissionDialogComponent implements OnInit {

    permission: Permission;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private permissionService: PermissionService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.permission.id !== undefined) {
            this.subscribeToSaveResponse(
                this.permissionService.update(this.permission));
        } else {
            this.subscribeToSaveResponse(
                this.permissionService.create(this.permission));
        }
    }

    private subscribeToSaveResponse(result: Observable<Permission>) {
        result.subscribe((res: Permission) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: Permission) {
        this.eventManager.broadcast({ name: 'permissionListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-permission-popup',
    template: ''
})
export class PermissionPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private permissionPopupService: PermissionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.permissionPopupService
                    .open(PermissionDialogComponent as Component, params['id']);
            } else {
                this.permissionPopupService
                    .open(PermissionDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
