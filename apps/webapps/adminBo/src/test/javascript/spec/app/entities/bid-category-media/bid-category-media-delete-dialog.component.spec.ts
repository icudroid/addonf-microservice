/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AdminBoTestModule } from '../../../test.module';
import { BidCategoryMediaDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/bid-category-media/bid-category-media-delete-dialog.component';
import { BidCategoryMediaService } from '../../../../../../main/webapp/app/entities/bid-category-media/bid-category-media.service';

describe('Component Tests', () => {

    describe('BidCategoryMedia Management Delete Component', () => {
        let comp: BidCategoryMediaDeleteDialogComponent;
        let fixture: ComponentFixture<BidCategoryMediaDeleteDialogComponent>;
        let service: BidCategoryMediaService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [BidCategoryMediaDeleteDialogComponent],
                providers: [
                    BidCategoryMediaService
                ]
            })
            .overrideTemplate(BidCategoryMediaDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(BidCategoryMediaDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BidCategoryMediaService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
