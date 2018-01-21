import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MediaTarget } from './media-target.model';
import { MediaTargetPopupService } from './media-target-popup.service';
import { MediaTargetService } from './media-target.service';

@Component({
    selector: 'jhi-media-target-delete-dialog',
    templateUrl: './media-target-delete-dialog.component.html'
})
export class MediaTargetDeleteDialogComponent {

    mediaTarget: MediaTarget;

    constructor(
        private mediaTargetService: MediaTargetService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mediaTargetService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'mediaTargetListModification',
                content: 'Deleted an mediaTarget'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-media-target-delete-popup',
    template: ''
})
export class MediaTargetDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private mediaTargetPopupService: MediaTargetPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.mediaTargetPopupService
                .open(MediaTargetDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
