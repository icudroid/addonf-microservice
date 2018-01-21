/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AdvertiserBoTestModule } from '../../../test.module';
import { MediaTargetDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/media-target/media-target-delete-dialog.component';
import { MediaTargetService } from '../../../../../../main/webapp/app/entities/media-target/media-target.service';

describe('Component Tests', () => {

    describe('MediaTarget Management Delete Component', () => {
        let comp: MediaTargetDeleteDialogComponent;
        let fixture: ComponentFixture<MediaTargetDeleteDialogComponent>;
        let service: MediaTargetService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdvertiserBoTestModule],
                declarations: [MediaTargetDeleteDialogComponent],
                providers: [
                    MediaTargetService
                ]
            })
            .overrideTemplate(MediaTargetDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MediaTargetDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MediaTargetService);
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
