/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { AdminBoTestModule } from '../../../test.module';
import { PermissionDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/permission/permission-delete-dialog.component';
import { PermissionService } from '../../../../../../main/webapp/app/entities/permission/permission.service';

describe('Component Tests', () => {

    describe('Permission Management Delete Component', () => {
        let comp: PermissionDeleteDialogComponent;
        let fixture: ComponentFixture<PermissionDeleteDialogComponent>;
        let service: PermissionService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [PermissionDeleteDialogComponent],
                providers: [
                    PermissionService
                ]
            })
            .overrideTemplate(PermissionDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PermissionDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PermissionService);
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
