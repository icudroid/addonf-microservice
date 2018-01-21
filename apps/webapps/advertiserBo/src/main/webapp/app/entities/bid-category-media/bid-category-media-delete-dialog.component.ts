import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { BidCategoryMedia } from './bid-category-media.model';
import { BidCategoryMediaPopupService } from './bid-category-media-popup.service';
import { BidCategoryMediaService } from './bid-category-media.service';

@Component({
    selector: 'jhi-bid-category-media-delete-dialog',
    templateUrl: './bid-category-media-delete-dialog.component.html'
})
export class BidCategoryMediaDeleteDialogComponent {

    bidCategoryMedia: BidCategoryMedia;

    constructor(
        private bidCategoryMediaService: BidCategoryMediaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bidCategoryMediaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bidCategoryMediaListModification',
                content: 'Deleted an bidCategoryMedia'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bid-category-media-delete-popup',
    template: ''
})
export class BidCategoryMediaDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bidCategoryMediaPopupService: BidCategoryMediaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.bidCategoryMediaPopupService
                .open(BidCategoryMediaDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
