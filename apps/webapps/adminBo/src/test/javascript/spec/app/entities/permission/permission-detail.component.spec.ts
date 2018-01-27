/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';

import { AdminBoTestModule } from '../../../test.module';
import { PermissionDetailComponent } from '../../../../../../main/webapp/app/entities/permission/permission-detail.component';
import { PermissionService } from '../../../../../../main/webapp/app/entities/permission/permission.service';
import { Permission } from '../../../../../../main/webapp/app/entities/permission/permission.model';

describe('Component Tests', () => {

    describe('Permission Management Detail Component', () => {
        let comp: PermissionDetailComponent;
        let fixture: ComponentFixture<PermissionDetailComponent>;
        let service: PermissionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [AdminBoTestModule],
                declarations: [PermissionDetailComponent],
                providers: [
                    PermissionService
                ]
            })
            .overrideTemplate(PermissionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PermissionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PermissionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Permission(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.permission).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
