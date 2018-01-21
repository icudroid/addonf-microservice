import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MediaUser } from './media-user.model';
import { MediaUserPopupService } from './media-user-popup.service';
import { MediaUserService } from './media-user.service';

@Component({
    selector: 'jhi-media-user-delete-dialog',
    templateUrl: './media-user-delete-dialog.component.html'
})
export class MediaUserDeleteDialogComponent {

    mediaUser: MediaUser;

    constructor(
        private mediaUserService: MediaUserService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mediaUserService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mediaUserListModification',
                content: 'Deleted an mediaUser'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-media-user-delete-popup',
    template: ''
})
export class MediaUserDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mediaUserPopupService: MediaUserPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mediaUserPopupService
                .open(MediaUserDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
